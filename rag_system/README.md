# Setting up the RAG system on Google Cloud
1. Upload all data into a **bucket** on Google Cloud
2. Create a **data store** from this bucket 
3. Create a **Cloud Function** based on the provided ```services_solana-chat.zip```
4. The RAGged LLM can now be accessed by the link provided by the Cloud Function, e.g. https://solana-chat-60707405365.us-central1.run.app 

# Using the RAGged LLM
Example: 
```curl -X POST https://solana-chat-60707405365.us-central1.run.app -H "Content-Type: application/json" -d '{"question":"What are the parameters for the sendTransaction function?"}'```