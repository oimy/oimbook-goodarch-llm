import Icon from "../../../components/icon";
import * as React from "react";
import {useState} from "react";

export default function ChatInput({
    isAnswering,
    onReceiveQuery,
    onReceiveAnswer,
}: {
    isAnswering: boolean;
    onReceiveQuery: (content: string) => Promise<number>;
    onReceiveAnswer: (content: string, isDone: boolean) => void;
}) {
    const [query, setQuery] = useState<string>("");

    const handleSubmitQuery = async (event: React.SubmitEvent) => {
        event.preventDefault();
        setQuery("");
        const contextId = await onReceiveQuery(query);

        try {
            const response = await fetch('/api/chat/talk', {
                method: 'POST',
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify({
                    contextId,
                    content: query,
                }),
            });
            if (!response.ok) {
                console.error("Streaming error:", response.statusText);
                onReceiveAnswer("죄송합니다. 답변에 실패했으니 다시 시도해주세요.", true);
                return;
            }

            const reader = response.body?.getReader();
            const decoder = new TextDecoder();
            let accumulatedAnswer = "";

            if (!reader) {
                return;
            }

            for (let i = 0; i < 1000; i++) {
                const {value, done} = await reader.read();
                if (done) {
                    console.log(accumulatedAnswer);
                    onReceiveAnswer(accumulatedAnswer, true);
                    break;
                }

                const chunkValue = decoder.decode(value);
                accumulatedAnswer += chunkValue;
                onReceiveAnswer(accumulatedAnswer, false);
            }
        } catch (err) {
            console.error("Streaming error:", err);
        }
    };

    return <div className={"w-100 d-flex flex-wrap justify-content-center bottom-0 mb-5"}>
        <form onSubmit={handleSubmitQuery}
              className={"p-3 ps-4 border rounded-4 shadow"}
              style={{width: "500px", backgroundColor: "white"}}>
            <fieldset className={"w-100 d-flex justify-content-between gap-4"} disabled={isAnswering}>
                <input type={"text"} value={query} onChange={(e) => setQuery(e.target.value)}
                       className={"border-0 flex-grow-1"}
                       required/>
                <button className={"btn"} type={"submit"}>
                    <Icon name={"arrow-up"}/>
                </button>
            </fieldset>
        </form>
    </div>;

}