from typing import Literal

from pydantic import BaseModel


class Chat(BaseModel):
    role: Literal['user', 'assistant']
    content: str
