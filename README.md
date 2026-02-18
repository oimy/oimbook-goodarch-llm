# Chatoim
## only dev mode supported

related post : [oimbook - 오래가는 코드가 강한 것이다]()

![demo gif]("https://github.com/oimy/oimbook-goodarch-llm/blob/main/demo/demo_1.gif?raw=true")


# Getting Start
### LLM

```bash
cd llm-serving-api
source ./.venv/bin/activate
fastapi run dev
```

### API

```bash
cd chatoim-api
./gradlew bootRun --args='--spring.profiles.active=local'
```


### UI

```
cd chatoim-ui
npm run dev
```

