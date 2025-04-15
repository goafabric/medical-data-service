Evaluation of Replication and Notification of Medical Data
(Inside a Container Orchestration Environment, Code enhanced by AI Assistance)

Goals
* Medical Record Notifications via Websockets
    * RECORD_LOCK, RECORD_UNLOCK, KIM_MESSAGE, RECORD_CHANGES, PATIENT_OPEN
    * Working for multiple User Frontends + Kubernetes Replicasets

* Replication of Medical Data via Kafka (e.g. Patient, Condition, Observation)
    * Retain order of the replicated data (e.g. PATIENT_CREATE CONDITION_UPDATE)
    * Logger Consumer (1st step), NOSQL Consumer (later)

