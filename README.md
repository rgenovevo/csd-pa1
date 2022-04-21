# CSD - Project Assigment #1

## Classes

<pre>
  src.main.java
    .client
      .TestClient       // Client main class to test and communicate with server.
    .server
      .Server           // Server main class to respond to client requests. 
      .ServerResource   // Resources for restful operations on server-side.
    .shared
      .Account          // Users can have multiple accounts to operate and control balance.
      .User *TODO*      // Identification and profile of a user.
</pre>

## Testing..

  1. Run Server class <br />
  2. Run TestClient class <br />
  
  <br />
  Client Terminal:
  <br />

  
    Request:    InboundJaxrsResponse{context=ClientResponse{method=POST, uri=http://localhost:8080/account/123, status=200, reason=OK}}
    Output:     New account created. {ID = 123}

    Request:    InboundJaxrsResponse{context=ClientResponse{method=GET, uri=http://localhost:8080/account/123, status=200, reason=OK}}
    Output:     Account balance retrieved. { ID = 123 ; Balance = 10 }

    Request:    InboundJaxrsResponse{context=ClientResponse{method=PUT, uri=http://localhost:8080/transaction/123/321/10, status=200, reason=OK}}
    Output:     Transaction from account 123 to 321 with money value of 10.
    
    
## Versions

<pre>
0.1.3
  Delete unused files.

0.1.1-0.1.2
  Updated README.md
  
0.1.0
  Base Project Assignment #1 
    - Server-Side Rest API with Jersey
    - First POST, PUT, GET methods
    - Client Test
</pre>
