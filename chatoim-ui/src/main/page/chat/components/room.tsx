import type {ChatMessage} from "../../../apis/chat/models.ts";
import "./room.css";
import Icon from "../../../components/icon";
import Markdown from "react-markdown";

export default function ChatRoom({
    messages,
    answerStream,
    isAnswering,
}: {
    messages: ChatMessage[];
    answerStream: string;
    isAnswering: boolean;
}) {

    return (
        <section className={"d-flex mt-5 fs-d9 w-100 justify-content-center"}>
            <div className={"d-flex flex-column z-1"} style={{width: "768px"}}>
                {messages.map(message => (
                    <div className={"message mt-4 message-" + message.role.toLowerCase()}>
                        <p className={"text-start"}
                           style={{whiteSpace: "pre-wrap"}}>
                            <Markdown >
                                {message.content}
                            </Markdown>
                        </p>
                    </div>
                ))}
                {isAnswering && (
                    <div className={"message mt-4 message-assistant message-loading"}>
                        <div className={"message-loading-border"}></div>
                        {answerStream !== "" ? (
                            <p className={"text-start"} style={{whiteSpace: "pre-wrap"}}>
                                <Markdown>
                                    {answerStream}
                                </Markdown>
                            </p>) : (
                            <Icon name={"spinner"} className={"fa-spin"}/>)}
                    </div>
                )}
            </div>
        </section>
    );

}