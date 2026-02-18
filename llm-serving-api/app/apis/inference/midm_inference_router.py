import asyncio
from typing import List

from fastapi import APIRouter
from starlette.responses import StreamingResponse

from app.schemas.chat import Chat
from app.services.inference.midm import midm_inference_service

router = APIRouter(prefix="/inference/midm")

inference_service = midm_inference_service

inference_lock = asyncio.Lock()


@router.post("")
async def infer_midm_stream(chats: List[Chat]):
    async def generate_infer():
        await inference_lock.acquire()
        generator = inference_service.infer(chats)
        try:
            for text in generator:
                yield text
        finally:
            inference_lock.release()

    return StreamingResponse(
        generate_infer(),
        media_type="text/event-stream",
    )
