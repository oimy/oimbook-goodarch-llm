export interface ChatContext {
    srl: number;
    title: string;
    createdAt: Date;
}

export interface ChatMessage {
    role: "USER" | "ASSISTANT";
    content: string;
    createdAt: Date;
}