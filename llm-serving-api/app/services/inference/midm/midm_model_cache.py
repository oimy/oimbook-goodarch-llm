import torch
from transformers import AutoTokenizer, AutoModelForCausalLM, TokenizersBackend, PreTrainedModel, \
    PreTrainedTokenizerFast, PreTrainedTokenizerBase, BitsAndBytesConfig

from app.services.inference import ModelCache


class MidmModelCache(ModelCache):
    def __init__(self, model_name: str):
        self._model_name = model_name
        self._tokenizer: TokenizersBackend | None = None
        self._model: PreTrainedModel | None = None

    def _make_tokenizer(self) -> PreTrainedTokenizerBase:
        tokenizer: PreTrainedTokenizerBase = AutoTokenizer.from_pretrained(self._model_name)  # noqa
        tokenizer.pad_token = tokenizer.eos_token
        return tokenizer

    def _make_model(self) -> PreTrainedModel:
        model = AutoModelForCausalLM.from_pretrained(
            self._model_name,
            dtype=torch.bfloat16,
            device_map="auto",
            trust_remote_code=True,
        )
        model.eval()
        torch.cuda.empty_cache()
        return model

    @property
    def tokenizer(self) -> TokenizersBackend:
        if not self._tokenizer:
            self._tokenizer = self._make_tokenizer()

        return self._tokenizer

    @property
    def model(self) -> PreTrainedModel:
        if not self._model:
            self._model = self._make_model()

        return self._model
