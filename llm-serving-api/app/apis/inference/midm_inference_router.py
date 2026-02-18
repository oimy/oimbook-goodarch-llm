from typing import List

from fastapi import APIRouter
from starlette.responses import StreamingResponse

from app.schemas.chat import Chat
from app.services.inference.midm import midm_inference_service

router = APIRouter(prefix="/inference/midm")

inference_service = midm_inference_service


@router.post("")
def infer_midm_stream(chats: List[Chat]):
    generator = inference_service.infer(chats)

    def generate_infer():
        for text in generator:
            yield text

    return StreamingResponse(
        generate_infer(),
        media_type="text/event-stream",
    )
