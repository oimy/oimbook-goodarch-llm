import torch
from transformers.pipelines import Pipeline, pipeline

from app.services.inference import PipeCache


class MidmPipeCache(PipeCache):
    def __init__(self, model_name: str):
        self._model_name = model_name
        self._pipe: Pipeline | None = None

    def _download_pipe(self):
        self._pipe = pipeline(
            task="text-generation",
            model=self._model_name,
            dtype=torch.float16,
            device_map="auto",
        )

    @property
    def pipe(self) -> Pipeline:
        if not self._pipe:
            self._download_pipe()

        return self._pipe
