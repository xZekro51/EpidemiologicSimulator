# Epidemiologic Simulator
Un simulatore Epidemiologico basilare sviluppato per un progetto durante il CdL "Metodologie di Programmazione". 
Il simulatore prende in input vari parametri, elencati qui di seguito.
* Numero persone nella Popolazione (**P**)
* Costo del tampone (**C**)
* Risorse dello stato (**R**)
* Infettività della malattia (**I**)
* Possibilità di sviluppo di sintomi (**S**)
* Letalità (inteso nel senso di possibilità che un malato muoia) (**L**)
* Durata della malattia (**D**)
* Incontri medi delle persone ogni giorno (**V**)

Inoltre vi è un parametro _Strategy_ che gestisce la strategia che il governo attuerà contro l'epidemia.
In un paragrafo seguente illustrerò come funziona e come ampliarlo.
## Dependencies
Il simulatore utilizza solo la libreria esterna `org.reflections`, reperibile al seguente [link](https://github.com/ronmamo/reflections).

## Interfaccia Grafica
L'interfaccia grafica è stata sviluppata utilizzando Swing. E' stata aggiunta anche una helper class
per aggiungere facilmente un label vicino ad un JComponent.

## Strategie
Le strategie sono state implementate dinamicamente. Per aggiungere una nuova strategia è necessario aggiungere una nuova
classe che implementi l'interfaccia `Strategy` ed i metodi `ApplyStrategy` e `Subscribe`.
In particolare il metodo **Subscribe** riceve in input un oggetto di tipo `StrategyListener`
che viene utilizzato per rendere dinamica l'applicazione delle strategie. Queste infatti possono essere attivate in 5 momenti diversi
a seconda dello `StrategyDelegate` a cui la nostra strategia si iscriverà.
Questi sono:
    
  * OnBeforeContagion
  * OnAfterContagion
  * OnAfterHealthUpdate
  * OnAfterResourceUpdate
  * OnDayEnd
  
Il simulatore implementa già 4 tipi di strategie, di seguito i dati medi su 10 simulazioni.
Le simulazioni sono state effettuate con i dati elencati di seguito.

`P = 10000, C = 5, R = 450000, I = 70, S = 20, L = 10, D = 45, V = 2`

 Strategy\Values | Common Outcome | Positive Outcome | Dead | Final Resources |
------------ | ------------- | --- | --- | ---
**No Action**| Economy_Collapse | 1 | 191 | -11657 | 
**Light Social Distancing** | Eradicated | 10 | 2 | 220534 |
**Heavy Social Distancing** | Eradicated | 10 | 1 | 74353|
**Encounter Tracing** | Economy_Collapse | 4 | 88| 168790| 

Da questa tabella si può notare come noonostante la strategia dell'**Heavy Social Distancing** sia quella più vessante a livello
economico, è anche la migliore in quanto quella che genera meno morti.

## Considerazioni
Come visto dalle simulazioni, il simulatore non è adatto per effettuare stime realistiche, in quanto basato su un modello che prende
in considerazione solo alcuni fattori. Nonostante ciò si può comunque vedere, seppur sommariamente, come alcune strategie siano più efficaci di altre oppure
le falle di logica in alcune strategie.
