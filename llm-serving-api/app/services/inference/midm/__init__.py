from .midm_inference_service import MidmInferenceStreamingService, MidmInferenceStreamingServiceImpl
from .midm_pipe_cache import MidmPipeCache
from .midm_model_cache import MidmModelCache

# midm_pipe_cache = MidmPipeCache()
# _ = midm_pipe_cache.pipe
midm_model_cache = MidmModelCache("google/gemma-2-2b-it")
midm_inference_service: MidmInferenceStreamingService = MidmInferenceStreamingServiceImpl(cache=midm_model_cache)
