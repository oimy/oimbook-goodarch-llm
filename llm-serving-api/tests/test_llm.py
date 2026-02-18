import torch
from transformers import pipeline

from dotenv import load_dotenv

load_dotenv(verbose=True)

pipe = pipeline(
    "text-generation",
    model="google/gemma-2-2b-it",
    dtype=torch.float16,
    device_map="auto",
)

messages = [
    {"role": "user", "content": "안녕하세요. 당신은 누구신가요?"},
]

r = pipe(
    messages,
    max_new_tokens=256,
    max_length=None,
    do_sample=True,
    temperature=0.7,
    top_p=0.9,
)

print(r)
