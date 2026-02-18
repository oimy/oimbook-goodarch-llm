import Icon from "../../../components/icon";
import type {ChatContext} from "../../../apis/chat/models.ts";

export default function ChatSidebar(
    {
        chatContexts,
        onClickNew,
        onClickChat,
    }: {
        chatContexts: ChatContext[];
        onClickNew: () => void;
        onClickChat: (contextId: number) => void
    }) {

    return (
        <aside className={"h-100 d-flex align-content-start text-start p-3 fs-d9"}
               style={{width: "260px", backgroundColor: "#f8f9fa"}}>
            <div className={"d-flex flex-column w-100"}>
                <div><span className={"fs-3"}>😍</span><span className={"fw-bold fs-4 ms-2"}>chatoimbook</span></div>
                <div className={"mt-4"}>
                    <button className={"btn btn-light w-100 rounded rounded-3 text-start"}
                            onClick={onClickNew}>
                        <Icon name={"edit"} className={"me-2"}/>
                        새 채팅
                    </button>
                </div>
                <div className={"mt-5"}>
                    <p className={"ps-3 text-secondary"}>내 채팅</p>
                    {chatContexts.map((context) =>
                        <button className={"btn btn-light w-100 rounded rounded-3 text-start border-0"}
                                onClick={() => onClickChat(context.srl)}>
                            {context.title}
                        </button>,
                    )}
                </div>
            </div>
        </aside>
    );
}