import "./index.css";
import ChatSidebar from "./components/sidebar.tsx";
import {useCallback, useEffect, useRef, useState} from "react";
import type {ChatContext, ChatMessage} from "../../apis/chat/models.ts";
import ChatRoom from "./components/room.tsx";
import ChatInput from "./components/input.tsx";

export default function Chat() {
    const [chatContextId, setChatContextId] = useState<number>();
    const [chatContexts, setChatContexts] = useState<ChatContext[]>([]);
    const [chatMessages, setChatMessages] = useState<ChatMessage[]>([]);
    const [isAnswering, setIsAnswering] = useState<boolean>(false);
    const [answerStream, setAnswerStream] = useState<string>("");


    const handleClickNew = () => {
        setChatContextId(undefined);
        loadContexts();
        setChatMessages([]);
    };


    const handleClickChat = (contextId: number) => {
        setChatContextId(contextId);
        fetch("/api/chat/messages?contextId=" + contextId)
            .then((res) => {
                if (!res.ok) {
                    throw new Error("Network response was not ok");
                }
                return res.json() as Promise<ChatMessage[]>;
            })
            .then((data) => {
                setChatMessages(data);
            })
            .catch((err) => {
                console.error(err);
            });
    };

    const handleReceiveQuery = async (content: string): Promise<number> => {
        let newContextId: number | undefined = undefined;
        if (chatContextId === undefined) {
            const res = await fetch("/api/chat/contexts", {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify({content: content}),
            });

            newContextId = await res.json();
            setChatContextId(newContextId);
            loadContexts();
        }
        console.log("receive query", chatContextId);
        setChatMessages((prev) => [...prev, {
            role: "USER",
            content,
            createdAt: new Date(),
        }]);
        setIsAnswering(true);
        return newContextId || chatContextId || -1;
    };

    const handleReceiveAnswer = (content: string, isDone: boolean) => {
        if (!isDone) {
            setAnswerStream(content);
        } else {
            setChatMessages((prev) => [...prev, {
                role: "ASSISTANT",
                content,
                createdAt: new Date(),
            }]);
            setAnswerStream("");
            setIsAnswering(false);
        }
    };

    const loadContexts = useCallback(() => {
        fetch("/api/chat/contexts")
            .then((res) => {
                if (!res.ok) throw new Error("Network response was not ok");
                return res.json() as Promise<ChatContext[]>;
            })
            .then((data) => {
                setChatContexts(data);
            })
            .catch((err) => {
                console.error(err);
            });
    }, []);

    useEffect(() => {
        loadContexts();
    }, [loadContexts]);

    const chatMessageContainerRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        if (!chatMessageContainerRef.current) {
            return;
        }

        chatMessageContainerRef.current.scrollTop = chatMessageContainerRef.current.scrollHeight;
    }, [answerStream]);

    return <div className="app w-100 h-100">
        <div className={"d-flex flex-row w-100 h-100"}>
            <ChatSidebar chatContexts={chatContexts} onClickNew={handleClickNew} onClickChat={handleClickChat}/>
            <div className={"d-flex flex-column align-content-center flex-grow-1 flex-wrap"}
                 style={{backgroundColor: "#fff9f2"}}>
                <div ref={chatMessageContainerRef}
                     id={"chat-message-container"}
                     className={"w-100 pb-5 flex-grow-1 overflow-y-auto"}
                     style={{maxHeight: "calc(100% - 170px)"}}>
                    <ChatRoom messages={chatMessages} answerStream={answerStream} isAnswering={isAnswering}/>
                </div>
                <div className={"w-100"} style={{height: "170px"}}>
                    <ChatInput onReceiveQuery={handleReceiveQuery}
                               onReceiveAnswer={handleReceiveAnswer}
                               isAnswering={isAnswering}/>
                </div>
            </div>
        </div>
    </div>;

}