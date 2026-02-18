import './app.css';
import Chat from "./main/page/chat";
import {library} from "@fortawesome/fontawesome-svg-core";
import {fas} from "@fortawesome/free-solid-svg-icons";
import "bootstrap/dist/css/bootstrap.min.css";
import "./css/text.css";
import "./css/input.css";

library.add(fas);


function App() {

    return (
        <Chat/>
    );
}

export default App;
