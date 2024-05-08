L’applicazione fa uso della libreria JavaFX

Per garantirne il corretto funzionamento è necessario includere le librerie che si possono scaricare qui: https://gluonhq.com/products/javafx/

Per includerle, fare tasto destro sulla cartella del progetto da Eclipse, andare su Proprietà, selezionare sul menù laterale “Java Build Path”, selezionare “Libraries” e cliccare Modules. Successivamente cliccare su “Add External JARs…” e selezionare tutti i file JAR delle librerie presenti nella cartella “lib” dentro il download precedentemente effettuato.

Sarà inoltre necessario modificare il filepath all’interno del progetto, per garantire la corretta lettura delle playlist e delle canzoni.

La variabile “standardFilePath” si trova all’interno del package model e all’interno della classe “Music Player”

Sostituire il percorso esistente con il percorso assoluto della cartella “progetto_finale_songs” nel vostro computer
