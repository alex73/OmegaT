Questa traduzione � frutto del lavoro di Valter Mura, copyright� 2012.

==============================================================================
  File Leggimi di OmegaT 2.0

  1.  Informazioni su OmegaT
  2.  Che cosa � OmegaT?
  3.  Installazione di OmegaT
  4.  Contributi a OmegaT
  5.  OmegaT genera problemi? Si ha bisogno di assistenza?
  6.  Informazioni sul rilascio

==============================================================================
  1.  Informazioni su OmegaT


Le informazioni pi� aggiornate su OmegaT possono essere reperite accedendo a:
      http://www.omegat.org/

Assistenza all'utente, nel gruppo utenti di Yahoo (multilingue), in cui � possibile ricercare negli archivi senza necessit� di iscrizione:
     http://groups.yahoo.com/group/OmegaT/

Richieste di miglioramenti (in Inglese), nel sito SourceForge:
     http://sourceforge.net/tracker/?group_id=68187&atid=520350

Segnalazione errori (in Inglese), nel sito SourceForge:
     http://sourceforge.net/tracker/?group_id=68187&atid=520347

==============================================================================
  2.  Che cosa � OmegaT?

OmegaT � uno strumento per la traduzione assistita da computer (CAT). � gratuito, ossia non si deve pagare nulla per usarlo, anche per un uso professionale ed � possibile modificarlo e, o in alternativa, redistribuirlo fintanto che viene rispettata la licenza per l'utente.

Le caratteristiche principali di OmegaT sono:
  - possibilit� di funzionare su qualsiasi sistema operativo che supporti Java
  - utilizzo di qualsiasi file TMX valido come memoria di riferimento
  - segmentazione flessibile della frase (grazie all'uso di un metodo di tipo SRX)
  - ricerche nel progetto e nelle memorie di traduzione di riferimento
  - ricerche dei file nei formati supportati in qualsiasi cartella 
  - concordanze parziali (fuzzy)
  - semplice gestione dei progetti che prevedono strutture di cartelle complesse
  - supporto dei glossari (ricerca terminologica) 
  - supporto dei correttori ortografici open source "al volo"
  - supporto dei dizionari StarDict
  - supporto dei servizi di traduzione automatica Google Translate
  - - documentazione ed esercitazioni chiare ed esaustive
  - localizzazione in pi� lingue

OmegaT � in grado di riconoscere immediatamente i seguenti formati di file formattati:

- formati di file di solo testo

  - file di testo ASCII (.txt, ecc.)
  - testo codificato (*.UTF8)
  - Java resource bundles (*.properties)
  - file PO (*.po)
  - file INI (codice=valore) (*.ini)
  - file DTD (*.DTD)
  - file DocuWiki (*.txt)
  - file di titoli SubRip (*.srt)
  - CSV locale Magento CE (*.csv)

- formati di file di testo con tag

  - OpenOffice.org / OpenDocument (*.odt, *.ott, *.ods, *.ots, *.odp, *.otp)
  - Microsoft Open XML (*.docx, *.xlsx, *.pptx)
  - (X)HTML (*.html, *.xhtml,*.xht)
  - compilatore Help HTML (*.hhc, *.hhk)
  - DocBook (*.xml)
  - XLIFF monolingua (*.xlf, *.xliff, *.sdlxliff)
  - QuarkXPress CopyFlowGold (*.tag, *.xtg)
  - file ResX (*.resx)
  - risorsa Android (*.xml)
  - LaTex (*.tex, *.latex)
  - file di guida (*.xml) e manuale (*.hmxp)
  - Typo3 LocManager (*.xml)
  - localizzazione WiX (*.wxl)
  - Iceni Infix (*.xml)
  - esportazione Flash XML (*.xml)
  - Wordfast TXML (*.txml)
  - Camtasia per Windows (*.camproj)

OmegaT pu� essere personalizzato per gestire anche altri tipi di formati di file.

OmegaT analizza automaticamente anche la struttura di cartelle pi� complessa per accedere poi a tutti i file riconosciuti e generare una struttura di cartelle di destinazione esattamente uguale a quella di partenza, comprendente anche le copie di tutti i file non riconosciuti.

Per iniziare a operare subito con OmegaT, avviare il programma e leggere la "Guida di avvio rapido" che viene proposta.

Il manuale per l'utente si trova nel pacchetto appena scaricato e vi si potr� accedere dal menu Aiuto dopo aver avviato OmegaT.

==============================================================================
 3. Installazione di OmegaT

3.1 Generale
Per funzionare, OmegaT richiede che sul sistema sia installato Java Runtime Environment (JRE) versione 1.5 o superiore. Sono ora disponibili i pacchetti OmegaT che includono il Java Runtime Environment, e che evitano agli utenti l'incombenza di selezionarlo, scaricarlo e installarlo. 

Se Java � gi� installato, un metodo per installare la versione corrente di OmegaT � usare "Java Web Start". 
A questo proposito, scaricare ed eseguire il file seguente:

   http://omegat.sourceforge.net/webstart/OmegaT.jnlp

Esso installer� l'ambiente corretto per il proprio computer e l'applicazione per il primo avvio. Non � necessario eseguire online le chiamate successive.

Durante l'installazione, in base al sistema operativo, si potrebbero ricevere diversi avvisi di sicurezza. Il certificato � firmato da "Didier Briel". 
I permessi che si concedono a questa versione (che potremmo chiamare "accesso illimitato al computer") sono gli stessi che si attribuiscono alla versione locale, come installati da una procedura descritta in seguito: essi consentono l'accesso al disco rigido del computer. I clic successivi su OmegaT.jnlp verificheranno e installeranno gli aggiornamenti, se presenti, e avvieranno OmegaT. 

Di seguito, sono descritti i metodi alternativi e gli strumenti per scaricare e installare OmegaT. 

Per gli utenti di Windows e Linux: se si � a conoscenza che il proprio sistema possiede gi� una versione adatta di JRE, � possibile installare la versione OmegaT senza JRE (indicata col nome della versione "Without_JRE"). 
Se si � in dubbio, raccomandiamo di usare la versione fornita con JRE. Essa � sicura: nonostante JRE sia gi� installato nel sistema, questa versione non interferir� con esso.

Per gli utenti di Linux: OmegaT funzioner� con l'implementazione Java open source fornita in molte distribuzioni Linux (per esempio Ubuntu), ma in tale caso � possibile riscontrare errori, problemi di visualizzazione o mancanza di alcune funzionalit�. Raccomandiamo dunque di scaricare e installare "Oracle Java Runtime Environment" (JRE) o il pacchetto OmegaT completo di JRE (quello .tar.bz2) contrassegnato "Linux"). Se si installa una versione di Java a livello di sistema, � necessario assicurarsi che questa sia nel proprio percorso di avvio o che sia richiamata in modo esplicito durante l'avvio di OmegaT. Se non si � esperti di Linux, raccomandiamo di installare una versione di OmegaT con JRE. Essa � sicura, dato che questo JRE "locale" non interferisce con gli altri JRE installati nel sistema.

Per gli utenti di Mac: JRE � gi� installato nel Mac OS X precedente alla versione 10.7 (Lion). Agli utenti di Lion verr� fatta una richiesta dal sistema (al primo avvio di un'applicazione che ha bisogno di Java), il quale eventualmente lo scaricher� e installer� in modo automatico.

Per Linux sui sistemi PowerPC: gli utenti devono scaricare il JRE di IBM, dato che Sun non fornisce una versione JRE per sistemi PPC. In questo caso, scaricarlo da:

    http://www.ibm.com/developerworks/java/jdk/linux/download.html 


3.2 Installazione
* Utenti di Windows: avviare il programma di installazione. Il programma di installazione pu� creare, se richiesto, collegamenti per l'avvio di OmegaT.

* Utenti di Linux: salvare ed estrarre l'archivio in una cartella di propria scelta; OmegaT � pronto per essere avviato. � possibile comunque eseguire un'installazione pi� pulita e semplice tramite lo script di installazione (linux-install.sh). Per utilizzare questo script, aprire una finestra di terminale (console), passare alla cartella in cui sono contenuti OmegaT.jar e lo script linux-install.sh, ed eseguire lo script col comando ./linux-install.sh.

* Utenti di Mac: copiare ed estrarre l'archivio OmegaT.zip in una cartella di propria scelta - essa conterr� il file indice della documentazione HTML e quello dell'applicazione OmegaT.app

* Altri sistemi (per es., Solaris, FreeBSD: per installare OmegaT, creare semplicemente una cartella a esso dedicata. Copiare ed estrarre l'archivio zip o tar.bz2 di OmegaT in questa cartella.

3.3 Avvio di OmegaT
Avviare OmegaT nei modi che seguono.

* Utenti di Windows:  se durante l'installazione � stato creato un collegamento nel desktop, fare doppio-clic sul collegamento. In alternativa, fare doppio-clic sul file OmegaT.exe. Se in Esplora risorse si visualizza il file OmegaT ma non OmegaT.exe modificare le impostazioni affinch� siano visibili le estensioni.

* Utenti di Linux: se si utilizza lo script di installazione fornito col programma, si dovrebbe riuscire ad avviare OmegaT con Alt+F2 poi omegat

* Utenti di Mac: fare doppio-clic sul file OmegaT.app.

* Dal proprio gestore di file (tutti i sistemi): fare doppio-clic sul file OmegaT.jar. Questo metodo funzioner� solo se il tipo di file .jar � associato con Java nel proprio sistema.

* Dalla riga di comando (tutti i sistemi): il comando per avviare OmegaT �:

cd <cartella dove si trova il file OmegaT.jar>

<nome e percorso del file eseguibile Java> -jar OmegaT.jar

(il file eseguibile Java � il file java in Linux e java.exe in Windows.
Se Java � installato a livello di sistema ed � nel percorso del comando, il percorso completo non deve essere indicato).

Personalizzazione dell'avvio di OmegaT:

* Utenti di Windows: il programma di installazione pu� creare collegamenti nel menu start, nel desktop e nell'area di avvio rapido. Per creare il collegamento, � possibile anche trascinare a mano il file OmegaT.exe nel menu start, nel desktop o nella barra di avvio rapido.

* Utenti di Linux: l'uso dello script Kaptain (omegat.kaptn), compreso nel pacchetto, fornisce un metodo pi� semplice per avviare OmegaT. Per utilizzare lo script � necessario prima installare Kaptain. � poi possibile utilizzare lo script di avvio Kaptain con 
Alt+F2
omegat.kaptn

Maggiori informazioni sullo script Kaptain e sull'aggiunta di elementi di menu e icone di avvio in Linux, sono reperibili nel Linux HowTo di OmegaT.

Utenti di Mac: trascinare OmegaT.app nella propria dock o nella barra degli strumenti di una finestra di Finder per poterlo avviare da qualsiasi posizione. Potete anche richiamarlo nell'area di ricerca di Spotlight.

==============================================================================
 4. Partecipare al progetto OmegaT

Per partecipare allo sviluppo di OmegaT, mettersi in contatto con gli sviluppatori all'indirizzo:
    http://lists.sourceforge.net/lists/listinfo/omegat-development

Per tradurre l'interfaccia utente di OmegaT, il manuale per l'utente o altri documenti correlati, leggere:
      
      http://www.omegat.org/en/translation-info.html

e iscriversi all'elenco dei traduttori:
      http://lists.sourceforge.net/mailman/listinfo/omegat-l10n

Per altri tipi di contributi, prima iscriversi al gruppo di utenti all'indirizzo:
      http://tech.groups.yahoo.com/group/omegat/

Avrete cos� un'idea di che cosa accade nel mondo di OmegaT...

  OmegaT � un lavoro originale di Keith Godfrey.
  Marc Prior � il coordinatore del progetto OmegaT.

Elenco dei contributi precedenti (in ordine alfabetico)

Contributi al codice:
  Zoltan Bartko
  Volker Berlin
  Didier Briel (gestore dello sviluppo)
  Kim Bruning
  Alex Buloichik (sviluppatore primario)
  Sandra Jean Chua
  Thomas Cordonnier
  Martin Fleurke  
  Wildrich Fourie
  Jean-Christophe Helary
  Thomas Huriaux
  Hans-Peter Jacobs
  Guido Leenders
  Ibai Lakunza Velasco
  Fabi�n Mandelbaum
  John Moran
  Maxym Mykhalchuk 
  Arno Peters
  Henry Pijffers 
  Briac Pilpr�
  Tiago Saboga
  Andrzej Sawula
  Benjamin Siband
  Rashid Umarov  
  Antonio Vilei
  Martin Wunderlich
  Michael Zakharov

Altri contributi:
  Sabine Cretella
  Dmitri Gabinski
  Jean-Christophe Helary (gestore della localizzazione)
  Vito Smolej (gestore della documentazione)
  Samuel Murray
  Marc Prior 
  e molte, molte altre persone che hanno contribuito

(se ritenete di aver contribuito in modo significativo al Progetto OmegaT ma il vostro nome non � presente nell'elenco, contattateci senza problemi).

OmegaT usa le seguenti librerie:

  HTMLParser di Somik Raha, Derrick Oswald et al (licenza LGPL)
  http://sourceforge.net/projects/htmlparser

  MRJ Adapter 1.0.8 di Steve Roy (licenza LGPL)
  http://homepage.mac.com/sroy/mrjadapter/

  VLDocking Framework 2.1.4 di VLSolutions (licenza CeCILL)
  http://www.vlsolutions.com/en/products/docking/

  Hunspell di L�szl� N�meth et al (licenza LGPL)

  JNA di Todd Fast, Timothy Wall et al (licenza LGPL)

  Swing-Layout 1.0.2 (licenza LGPL)

  Jmyspell 2.1.4 (licenza LGPL)

  JAXB 2.1.7 (GPLv2 + classpath exception)

==============================================================================
 5.  OmegaT genera problemi? Si ha bisogno di assistenza?

Prima di segnalare un difetto, accertarsi di aver consultato attentamente la documentazione. Ci� che viene proposto potrebbe essere una caratteristica particolare di OmegaT che si � appena scoperta. Se si apre il file di registro di OmegaT, e nello stesso vengono riportate parole come "Error", "Warning", "Exception" oppure "died unexpectedly", allora si � in presenza di un errore importante (il file log.txt viene memorizzato nella cartella delle preferenze dell'utente; per il percorso di memorizzazione si rimanda al manuale).

La successiva cosa da fare � di chiedere conferma di quanto � accaduto agli altri utenti, in modo da verificare se si tratta di un problema gi� segnalato. Si pu� anche accedere alla pagina di segnalazione degli errori di SourceForge. Inviare una segnalazione di errore solo quando si � sicuri di essere stati i primi ad aver rilevato una sequenza riproducibile di un evento che ha generato qualche cosa che non sarebbe dovuta accadere.

Una qualsiasi segnalazione di errore dovrebbe prevedere tre elementi:
  - sequenza operativa da riprodurre,
  - che cosa ci si aspettava di ottenere, e
  - che cosa, invece, si � ottenuto.

Si possono allegare copie di file, parti del file di registro, schermate e tutto ci� che si ritiene possa essere d'aiuto agli sviluppatori per il reperimento e la correzione dell'errore.

Per accedere agli archivi del gruppo di utenti:
     http://groups.yahoo.com/group/OmegaT/

Per accedere alla pagina delle segnalazioni degli errori e registrare
una nuova segnalazione di errore:
     http://sourceforge.net/tracker/?group_id=68187&atid=520347

Per seguire il corso degli eventi relativi a una segnalazione di errore ci si dovrebbe iscrivere come utente Source Forge.

==============================================================================
6.   Informazioni sul rilascio

Per informazioni particolareggiate su questo rilascio, e tutti quelli precedenti, si veda il file "changes.txt".


==============================================================================
