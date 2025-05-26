# Movies Now – Android App (Kotlin + Jetpack Compose)

Aplicativo mobile funcional para Android, desenvolvido como parte do **Desafio Técnico – Desenvolvedor Mobile (iOS e Android)**.  
Exibe uma lista de filmes mockados a partir de uma **API GraphQL simulada localmente** (arquivo JSON) e permite visualização de detalhes com trailer embutido.

---

##  Funcionalidades obrigatórias

Tela de **listagem de filmes** com:
- Imagem
- Título
- Ano
- Nota

Tela de **detalhes do filme** com:
- Imagem de destaque
- Título
- Descrição
- Ano de lançamento
- Duração
- Nota
- **Miniplayer com trailer (YouTube embutido)**

**Consumo via GraphQL (simulado localmente)**

---

## Arquitetura adotada

- 🔹 **MVVM**
- 🔹 Separação por pacotes:
  - `features.movies.ui` → Telas
  - `features.movies.viewmodel` → ViewModel
  - `features.movies.domain` → Modelo de dados
- 🔹 Leitura do JSON local como simulação de requisição GraphQL
- 🔹 YouTubePlayerView para trailer

---

## Como rodar o projeto

1. Clone o repositório:
   ```bash
   git clone https://github.com/seuusuario/moviesnow_android.git
   ```

2. Abra no **Android Studio**

3. Coloque o arquivo `filmes.json` na pasta:
   ```
   app/src/main/assets/
   ```

4. Execute no emulador ou dispositivo:
   ```
   Shift + F10 (ou botão Run)
   ```

---

## 🧪 Diferenciais implementados

- Jetpack Compose (UI declarativa moderna)
- Miniplayer com YouTubePlayer embutido
- Responsivo para diferentes tamanhos de tela
- Animações suaves e uso de Material Design

---

## Estrutura de pacotes

```
com.moviesnow
├── features.movies
│   ├── ui
│   ├── viewmodel
│   └── domain
├── graphql
├── MainActivity.kt
└── assets/
    └── filmes.json
```

---

## Decisões técnicas

- Uso de `LiveData` com `observeAsState` em Compose
- `LaunchedEffect` para carregamento de dados
- Coil para imagens
- Simulação de GraphQL com JSON estático

---

## Observações

- O trailer é exibido com `YouTubePlayerView`
- O app funciona offline com base no arquivo `filmes.json`
- Ideal para expansão com API real

---

## 👨‍💻 Autor

Desenvolvido por [Rafael Conceição](mailto:rafael.conceicao.sp@outlook.com)