from fastapi import APIRouter

hello_router = APIRouter(prefix="/hello")


@hello_router.get("")
async def hello():
    return {"message": "hello world!"}
