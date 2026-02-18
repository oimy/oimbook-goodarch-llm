from abc import ABC, abstractmethod
from typing import List, Iterable

from transformers import Pipeline, PreTrainedModel, PreTrainedTokenizerFast, PreTrainedTokenizerBase

from app.schemas.chat import Chat


class PipeCache(ABC):
    @property
    @abstractmethod
    def pipe(self) -> Pipeline:  ...


class ModelCache(ABC):
    @property
    @abstractmethod
    def tokenizer(self) -> PreTrainedTokenizerBase: ...

    @property
    @abstractmethod
    def model(self) -> PreTrainedModel: ...


class InferenceStreamingService(ABC):
    @abstractmethod
    def infer(self, chats: List[Chat]) -> Iterable[str]: ...
