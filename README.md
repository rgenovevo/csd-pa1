# CSD - Project Assigment #1

## Classes

<pre>
PA1
  .certs                    // Certificates, TrustStores and KeyStores
  .config                   // Configs for BFT-SMaRt
  .src.main.java
    .client
      .TestClient           // Client main class to test and communicate with server.
    .server
      .proxy
        .Server             // Server main class to respond to client requests. 
        .ServerResource     // Resources for restful operations on server-side.
      .replica
        .ServerReplica      // Replicas main class to implement bft-smart.
      .ServerTypeRequest    // Request types from the server (enum)
    .shared
      .Account              // Users can have multiple accounts to operate and control balance.
      .Ledger               // Ledger with all transactions in order and all account balances.
      .Transaction          // Origin, destination and amount of a transaction.
      .User                 // Identification and profile of a user.
</pre>

## Testing..

  1. Run ServerReplica classes (4) with different arguments from <0-3> <br />
  2. Run Server class <br />
  3. Run TestClient class <br />
  
  <br />
  Client Terminal (1st Time):
  <br />

  
    Request:    InboundJaxrsResponse{context=ClientResponse{method=GET, uri=http://localhost:8080/ledger, status=302, reason=Found}}
    Output:     
     Empty...
    
    Request:    InboundJaxrsResponse{context=ClientResponse{method=GET, uri=http://localhost:8080/global, status=302, reason=Found}}
    Output:     Global ledger value: 0
    
    Request:    InboundJaxrsResponse{context=ClientResponse{method=POST, uri=http://localhost:8080/account/123, status=201, reason=Created}}
    Output:     New account created. {ID = 123}
    
    Request:    InboundJaxrsResponse{context=ClientResponse{method=POST, uri=http://localhost:8080/account/321, status=201, reason=Created}}
    Output:     New account created. {ID = 321}
    
    Request:    InboundJaxrsResponse{context=ClientResponse{method=POST, uri=http://localhost:8080/account/123, status=409, reason=Conflict}}
    Output:     Account already exists.
    
    Request:    InboundJaxrsResponse{context=ClientResponse{method=GET, uri=http://localhost:8080/account/123, status=302, reason=Found}}
    Output:     Account balance retrieved. { ID = 123 ; Balance = 10 }
    
    Request:    InboundJaxrsResponse{context=ClientResponse{method=GET, uri=http://localhost:8080/account/456, status=404, reason=Not Found}}
    Output:     Account doesn't exists.
    
    Request:    InboundJaxrsResponse{context=ClientResponse{method=PUT, uri=http://localhost:8080/transaction/321/123/10, status=202, reason=Accepted}}
    Output:     Transaction from account 321 to 123 with money value of 10.
    
    Request:    InboundJaxrsResponse{context=ClientResponse{method=GET, uri=http://localhost:8080/account/123, status=302, reason=Found}}
    Output:     Account balance retrieved. { ID = 123 ; Balance = 20 }
    
    Request:    InboundJaxrsResponse{context=ClientResponse{method=GET, uri=http://localhost:8080/account/321, status=302, reason=Found}}
    Output:     Account balance retrieved. { ID = 321 ; Balance = 0 }
    
    Request:    InboundJaxrsResponse{context=ClientResponse{method=GET, uri=http://localhost:8080/ledger, status=302, reason=Found}}
    Output:     
     System -> 123 : 10
     System -> 321 : 10
     321 -> 123 : 10
    
    Request:    InboundJaxrsResponse{context=ClientResponse{method=GET, uri=http://localhost:8080/account/123, status=302, reason=Found}}
    Output:     Account balance retrieved. { ID = 123 ; Balance = 20 }
    
    Request:    InboundJaxrsResponse{context=ClientResponse{method=PUT, uri=http://localhost:8080/transaction/321/123/10, status=406, reason=Not Acceptable}}
    Output:     Account (origin or destination) doesn't exists or origin doesn't have balance.
    
    Request:    InboundJaxrsResponse{context=ClientResponse{method=GET, uri=http://localhost:8080/account/123, status=302, reason=Found}}
    Output:     Account balance retrieved. { ID = 123 ; Balance = 20 }
    
    Request:    InboundJaxrsResponse{context=ClientResponse{method=GET, uri=http://localhost:8080/account/321, status=302, reason=Found}}
    Output:     Account balance retrieved. { ID = 321 ; Balance = 0 }
    
    Request:    InboundJaxrsResponse{context=ClientResponse{method=PUT, uri=http://localhost:8080/transaction/123/321/10, status=202, reason=Accepted}}
    Output:     Transaction from account 123 to 321 with money value of 10.
    
    Request:    InboundJaxrsResponse{context=ClientResponse{method=GET, uri=http://localhost:8080/account/321, status=302, reason=Found}}
    Output:     Account balance retrieved. { ID = 321 ; Balance = 10 }
    
    Request:    InboundJaxrsResponse{context=ClientResponse{method=GET, uri=http://localhost:8080/account/123, status=302, reason=Found}}
    Output:     Account balance retrieved. { ID = 123 ; Balance = 10 }
    
    Request:    InboundJaxrsResponse{context=ClientResponse{method=POST, uri=http://localhost:8080/account/432, status=201, reason=Created}}
    Output:     New account created. {ID = 432}
    
    Request:    InboundJaxrsResponse{context=ClientResponse{method=GET, uri=http://localhost:8080/ledger, status=302, reason=Found}}
    Output:     
     System -> 123 : 10
     System -> 321 : 10
     321 -> 123 : 10
     123 -> 321 : 10
     System -> 432 : 10
    
    Request:    InboundJaxrsResponse{context=ClientResponse{method=GET, uri=http://localhost:8080/global, status=302, reason=Found}}
    Output:     Global ledger value: 30
    
    Request:    InboundJaxrsResponse{context=ClientResponse{method=GET, uri=http://localhost:8080/total/123-321-654, status=302, reason=Found}}
    Output:     Total value of the accounts {IDs = [123, 321, 654]}: 20
    
    Request:    InboundJaxrsResponse{context=ClientResponse{method=GET, uri=http://localhost:8080/extract/123, status=302, reason=Found}}
    Output:     
     System -> 123 : 10
     321 -> 123 : 10
     123 -> 321 : 10
    
    Request:    InboundJaxrsResponse{context=ClientResponse{method=GET, uri=http://localhost:8080/extract/321, status=302, reason=Found}}
    Output:     
     System -> 321 : 10
     321 -> 123 : 10
     123 -> 321 : 10
    
    
## Versions

<pre>
0.3.0
  TLS v1.2 for Security Protocol
    - Client/Server authentication
    - HTTPS encrypted messages
    - Certificates, TrustStores and KeyStores

0.2.3
  Completed and tested all methods.
    * CreateAccount (LOAD_MONEY)
    * SendTransaction
    * GetBalance
    * GetExtract
    * GetTotalValue
    * GetGlobalLedgerValue
    * GetLedger

0.2.0
  BFT-SMaRt implementation and Replica creation
    - Server Proxy implementation
    - Server Replica implementation
    - Updated Client Test

0.1.4
  Updated .gitignore

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
