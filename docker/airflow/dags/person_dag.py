import datetime
import json

from airflow import DAG
from airflow.providers.common.sql.operators.sql import SQLExecuteQueryOperator
from airflow.providers.apache.kafka.operators.produce import ProduceToTopicOperator
from airflow.operators.empty import EmptyOperator

oracle_conn_id = 'oracle_conn'

def producer(data):
    key = b'person-key'
    value = json.dumps(data).encode('utf-8')
    yield key, value

person_dag = DAG(
    dag_id="person_dag",
    start_date=datetime.datetime(2021, 1, 1),
    schedule=None,
)

#task=EmptyOperator(task_id="task", dag=person_dag)

primeira_task=SQLExecuteQueryOperator(dag=person_dag,task_id="primeira_task", do_xcom_push=True, sql="SELECT * FROM airflow.person",conn_id=oracle_conn_id,show_return_value_in_logs='True')

segunda_task=ProduceToTopicOperator(kafka_config_id="kafka_conn",task_id="segunda_task",topic="person.topic",producer_function=producer, producer_function_kwargs={"data": "{{ti.xcom_pull(task_ids='primeira_task', key='return_value')}}"})

primeira_task >> segunda_task