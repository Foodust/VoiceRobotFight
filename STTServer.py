# 필요한 라이브러리 설치: pip install websockets speechrecognition
import asyncio
import websockets
import speech_recognition as sr

async def recognize_audio(websocket, path):
    recognizer = sr.Recognizer()
    with sr.Microphone() as source:
        print("Listening...")
        audio = recognizer.listen(source)

    try:
        text = recognizer.recognize_google(audio)
        print(f"Recognized text: {text}")
        await websocket.send(text)
    except sr.UnknownValueError:
        print("Google Speech Recognition could not understand audio")
    except sr.RequestError as e:
        print(f"Could not request results from Google Speech Recognition service; {e}")

async def main():
    print("starting server")
    start_server = websockets.serve(recognize_audio, "localhost", 8765)
    print("start server")
    await start_server
    await asyncio.Future()  # Run forever

asyncio.run(main())
