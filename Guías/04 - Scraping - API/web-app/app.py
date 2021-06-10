from flask import Flask, render_template, request, jsonify
import pymongo

app = Flask(__name__)
app.config["JSON_AS_ASCII"] = False

@app.route('/', methods=['GET', 'POST'])
def home():
    if request.method == 'GET':
        return render_template('home.html')

    elif request.method == 'POST':
        search_input = request.form['search-input']
        data = search(search_input)

        return render_template('data_display.html', 
                               search_input=search_input,
                               data=data)

@app.route('/api/<doc_id>', methods=['GET'])
def api_get_document(doc_id):
    client = pymongo.MongoClient("mongodb://localhost:27017/")
    db = client["test"]

    db_collection = db.emol
    result = db_collection.find({"id": int(doc_id)})

    data = []
    for doc in result:
        data.append({"title": doc["title"], "content": doc["content"]})

    answer = {"data": data}
    return jsonify(answer)

def search(search_input):
    # collection puede ser summary, managers o directors
    client = pymongo.MongoClient("mongodb://localhost:27017/")
    db = client["test"]

    db_collection = db.emol

    search_pre = map(lambda x: f"\"{x.strip()}\"", search_input.split())
    search_string = " ".join(search_pre)
    
    result = db_collection.find({"$text": {"$search": search_string}})
    data = []
    for doc in result:
        data.append(doc)
    return data


if __name__ == "__main__":
    app.run(debug=True)