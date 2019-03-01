# Installazione Multichain

Scaricare l'ultima versione di multichain.  
Dal terminale:

```su (enter root password)
cd /tmp
wget https://www.multichain.com/download/multichain-2.0-beta-2.tar.gz
tar -xvzf multichain-2.0-beta-2.tar.gz
cd multichain-2.0-beta-2
mv multichaind multichain-cli multichain-util /usr/local/bin (to make easily accessible on the command line)

exit (to return to your regular user)
```

## Creazione blockchain (Nodo primario)
```
multichain-util create [nome-chain]
   
// Comando per inizializzare blockchain
multichaind [nome-chain] -daemon
```

## Creazione stream
```
multichain-cli [nome-chain] create stream [nome-stream] false
multichain-cli [nome-chain] subscribe [nome-stream]
```

## Collegamento secondo nodo alla blockchain

```
multichaind [nome-chain]@[indirizzo-nodo-primario]:[port] -daemon
```
Il secondo nodo non potrà accedere alla blockchain in quanto non dispone dei permessi necessari.
Attribuiamo i permessi dal nodo primario. In questo verranno visuallizate delle scelte:

```
multichain-cli [nome-chain] grant [indirizzo-nodo-secondario] connect

```

Se vogliamo abilitare il nodo secondario a scrivere sulla chain, dobbiamo dargli il permesso:
Dal nodo primario:

```
multichain-cli [nome-chain] grant [indirizzo-nodo-secondario] [nome-stream].write

```

Ora dal nodo secondario: 

```
multichain-cli [nome-chain] subscribe [nome-stream]
multichaind [nome-chain] -daemon

```
Per maggiori dettagli visitare <https://www.multichain.com/developers/>

## Configurazione Java
Necessario installare web server, ad esempio Apache Tomcat v9.0.

Per il corretto collegamento del software alla chain sono necessarie le credenziali di accesso alla blockchain, in particolar modo la password e la porta rpc.
Per individuare la password, dopo aver lanciato la blockchain, dal terminale eseguire:

```
cat ~/.multichain/[nome-chain]/multichain.conf

```
Mentre per individuare la porta rpc:

```
grep rpc-port ~/.multichain/[nome-chain]/params.dat

```
Per quanto riguarda l'username, è settato di default come "multichainrpc".

Le informazioni precedentemente individuate dovranno essere sostituite nei seguenti file:

* Cerca.java
* FileUploadDettagli.java
* VisualizzaDettagli.java
* index.jsp



