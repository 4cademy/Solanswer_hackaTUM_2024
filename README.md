# JetSetters x Solanswer

## Overview
**This project consists of three parts.**

1) A workflow to enhance LLMs with documentation and ecosystem-related data via Google Cloud's Retrieval Augmented Generation (RAG) system. As an example, we enhance `Gemini 1.5 Pro` with the `Solana Developer Documentation`.
2) The `JetSetters Plugin` hooks into the Google Cloud backend to make use of our enhanced AI model. It is designed to provide developers with instant AI-driven documentation and code insights directly within the JetBrains IDEs. (see installation guide below)
3) The `Solanswer Website` (https://solanswer.vercel.app/) provides a web version of our AI agent. It is capable of answering in-depth questions regarding the Solana Documentation.

## Features
- **Instant Documentation Access:** Ask coding questions and receive real-time answers from the Solana Developer Docs or all other resources.
- **User-Friendly Interface:** Simple input/output fields for engaging with the AI chatbot.
- **More in-depth answers:** In domain-specific questions (example case: Solana Dev Environment) our AI Agent gives more in-depth answers than the GitHub Copilot & JetBrains AI
- **A Blueprint for other environments:** The workflow, plugin and website we built can be used plug-and-play with other vector datasets (i.e. other documentations, GitHub repos, ...)

## <a name="Installation"></a> Installation of `JetSetters Plugin`

### Prerequisites
- A compatible JetBrains IDE (e.g., IntelliJ IDEA, PyCharm, etc.)

### Downloading the Plugin
1. **Download the .zip File:**
   You can download the latest version of the JetSetters plugin: [here](https://github.com/shumancheng/JetSetters/raw/refs/heads/main/Plugin-1.0-SNAPSHOT.zip)

### Installing the Plugin
1. Open your JetBrains IDE.
2. Go to `File` > `Settings` (or `Preferences` on macOS).
3. In the left pane, select `Plugins`.
4. Click on the gear icon ⚙️ in the top-right corner and choose `Install Plugin from Disk...`.
5. Navigate to the location where you downloaded the JetSetters .ZIP file and select it.
6. Click `OK` to install the plugin.
7. Restart your IDE to activate the plugin.

## Usage
1. After restarting, you will find the JetSetters plugin in your IDE under `Tools`.
2. Click on `JetSetters Chat Bot` to launch it.
3. Enter your coding question in the input field and click the **Send** button to receive an answer. (it takes a couple of seconds to get an answer).
4. Explore the output field for documentation and helpful resources related to your query.

## Contributing
We welcome contributions to JetSetters! If you would like to contribute, please fork the repository, create a new branch, and submit a pull request.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments
Thanks to the JetBrains community for their support and to the developers of the Solana documentation and Google Cloud RAG system for providing the resources that made this plugin possible.


---

**Happy Coding! 🚀**
