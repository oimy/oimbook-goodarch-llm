from dotenv import load_dotenv
from fastapi import FastAPI

from app.apis.hello import hello_router
from app.apis.inference import inference_router

load_dotenv()

app = FastAPI()

app.include_router(hello_router)
app.include_router(inference_router)