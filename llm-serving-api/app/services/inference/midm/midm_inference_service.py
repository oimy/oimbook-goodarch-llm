import asyncio
import threading
from abc import ABC
from typing import List, Iterable, cast

import torch
from transformers import TextIteratorStreamer, AutoTokenizer

from app.schemas.chat import Chat
from app.services.inference import InferenceStreamingService, ModelCache


class MidmInferenceStreamingService(InferenceStreamingService, ABC):
    ...


class MidmInferenceStreamingServiceImpl(MidmInferenceStreamingService):
    def __init__(self, cache: ModelCache):
        self._cache = cache

    def infer(self, chats: List[Chat]) -> Iterable[str]:
        if not chats or chats[-1].role == 'assistant':
            return

        tokenizer = self._cache.tokenizer
        model = self._cache.model
        chat_dicts = [chat.model_dump() for chat in chats]

        inputs = tokenizer.apply_chat_template(
            chat_dicts,
            add_generation_prompt=True,
            tokenize=True,
            return_dict=True,
            return_tensors="pt",
        ).to(model.device)

        streamer = TextIteratorStreamer(
            tokenizer=cast(AutoTokenizer, cast(object, tokenizer)),
            skip_special_tokens=True,
            skip_prompt=True,
        )
        generation_kwargs = dict(
            **inputs,
            max_new_tokens=256,
            do_sample=True,
            top_p=0.9,
            temperature=0.7,
            use_cache=True,
            pad_token_id=tokenizer.eos_token_id,
            streamer=streamer
        )

        def _generate():
            try:
                with torch.inference_mode():
                    model.generate(**generation_kwargs)
            except Exception as e:
                print(f"🔥 Thread Error: {e}")

        threading.Thread(target=_generate, daemon=True).start()

        for text in streamer:
            yield text
