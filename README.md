# Travel-Agency: Sistema Informativo per Agenzia di Viaggi Studio

Travel-Agency è un sistema informativo progettato per un'agenzia che organizza viaggi studio per studenti universitari. L'obiettivo è organizzare ogni dettaglio della vacanza in modo tale che chi parte debba solo preparare le valigie.

## Obiettivi del Sistema

Il sistema Travel-Agency consente l'esecuzione di vari casi d'uso, suddivisi tra leader di gruppo, amministratori, utenti registrati e utenti generici.

### Casi d'Uso

#### Leader del Gruppo

1. **Prenotazione Settimane:** Il leader può effettuare una prenotazione per una o più settimane proposte dall'agenzia.
2. **Visualizzazione Dettagli Prenotazioni:** Il leader può vedere i dettagli di ciascuna prenotazione effettuata.
3. **Cancellazione Prenotazioni:** Il leader può eliminare le prenotazioni effettuate.
4. **Visualizzazione Dettagli del Gruppo:** Il leader può visionare i dettagli del gruppo di cui è leader.

#### Amministratore

1. **Aggiunta Settimane Disponibili:** L'amministratore può aggiungere nuove settimane disponibili per essere prenotate dai leader di ciascun gruppo.
2. **Visualizzazione Dettagli Settimane:** L'amministratore può vedere i dettagli relativi a ciascun turno settimanale.
3. **Modifica e Cancellazione Settimane:** L'amministratore può modificare o cancellare settimane disponibili.
4. **Gestione Utenti:** L'amministratore può vedere tutti gli utenti iscritti alla piattaforma, vederne i dettagli ed eliminarli solo nel caso non abbiano fatto alcuna prenotazione.

#### Utente Registrato

1. **Visualizzazione Prenotazioni del Gruppo:** L'utente registrato può vedere le prenotazioni del proprio gruppo con tutti i dettagli.

#### Utente Generico

1. **Navigazione e Visualizzazione:** L'utente generico può navigare nel sito, vedere le diverse destinazioni proposte e altre funzionalità di sola visualizzazione.

## Autenticazione e Registrazione al Sistema

Il sistema prevede un diverso funzionamento in base al tipo di utente. Gli utenti devono registrarsi e accedere al sistema utilizzando le credenziali del proprio account.

### Tipi di Utente

1. **Utente non Autenticato:** Gli utenti non autenticati possono navigare nel sito e vedere le diverse destinazioni proposte e altre funzionalità di sola visualizzazione.
2. **Leader del Gruppo:** I leader del gruppo possono effettuare prenotazioni, visualizzare e cancellare prenotazioni, e visualizzare i dettagli del gruppo.
3. **Amministratore:** Gli amministratori possono gestire settimane disponibili, visualizzare e gestire dettagli delle settimane, e gestire utenti iscritti.
4. **Utente Registrato:** Gli utenti registrati possono vedere le prenotazioni del proprio gruppo con i dettagli.

### Modalità di Accesso

Gli utenti possono accedere al sistema tramite:
- **Credenziali del proprio account, previa registrazione**
- **Account Google**

## Tecnologie Utilizzate

- **Backend:** Spring Boot
- **Database:** PostgreSQL

## Istruzioni per l'Installazione

1. **Clona il repository:**
   ```bash
   git clone https://github.com/tuo-repo/travel-agency.git
   cd travel-agency
