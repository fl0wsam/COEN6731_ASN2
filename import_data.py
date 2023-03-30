import pandas as pd
from pymongo import MongoClient
from dotenv import load_dotenv
import os

# Load environment variables
load_dotenv()

# Read the CSV file
data_file = 'C:\\Users\\samij\\OneDrive\\Desktop\\ASN2\\nces330_20.csv'
df = pd.read_csv(data_file)

# Replace any empty values with None
df = df.where(pd.notnull(df), None)

# Connect to MongoDB Atlas
mongo_user = os.getenv('MONGO_USER')
mongo_password = os.getenv('MONGO_PASSWORD')
mongo_uri = f"mongodb+srv://{mongo_user}:{mongo_password}@cluster0.kw9bxn7.mongodb.net/myFirstDatabase?retryWrites=true&w=majority"

client = MongoClient(mongo_uri)

# Access the database and collection
db = client['education']
collection = db['EduCostStat']

# Insert the records into the EduCostStat collection
collection.insert_many(df.to_dict('records'))

# Close the MongoDB connection
client.close()

print('Data import completed.')
