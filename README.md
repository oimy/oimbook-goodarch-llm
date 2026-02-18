# Chatoim
## only dev mode supported

related post : [oimbook - 오래가는 코드가 강한 것이다]()

![demo gif](https://github.com/user-attachments/assets/eadbfdc0-953f-455a-b0e8-a1d5e883c96a)

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

