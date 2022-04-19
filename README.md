# Confiabilidade de Sistemas Distribuidos - Project Aassigment #1

## Classes
  src.main.java
    .client
      .TestClient       // Client main class to test and communicate with server.
    .server
      .Server           // Server main class to respond to client requests. 
      .ServerResource   // Resources for restful operations on server-side.
    .shared
      .Account          // Users can have multiple accounts to operate and control balance.
      .User *TODO*      // Identification and profile of a user.

## Testing..
  1. Run Server class
  2. Run TestClient class
  
  Client Terminal:
  '''
    Request:    InboundJaxrsResponse{context=ClientResponse{method=POST, uri=http://localhost:8080/account/123, status=200, reason=OK}}
    Output:     New account created. {ID = 123}

    Request:    InboundJaxrsResponse{context=ClientResponse{method=GET, uri=http://localhost:8080/account/123, status=200, reason=OK}}
    Output:     Account balance retrieved. { ID = 123 ; Balance = 10 }

    Request:    InboundJaxrsResponse{context=ClientResponse{method=PUT, uri=http://localhost:8080/transaction/123/321/10, status=200, reason=OK}}
    Output:     Transaction from account 123 to 321 with money value of 10.
  '''
