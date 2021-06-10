from urllib.request import urlopen
from bs4 import BeautifulSoup
import json
import ssl

context = ssl._create_unverified_context()

page = urlopen('http://www.emol.com', context=context).read()
soup = BeautifulSoup(page)
mostViewed = soup.findAll("div",
                         {"class": "caja_contenedor_masvistos_modulo_texto"})
print("###")
urlViewed = []
for div in mostViewed:
    if div.a:
        urlViewed.append(div.a["href"])

list_news = []

count = 1
for url in urlViewed:
    print("### Noticia {}: ".format(count))
    try:
        page = urlopen("http://www.emol.com{}".format(url), context=context).read()
        soup = BeautifulSoup(page)
    except:
        print("Error al intentar recuperar noticia.")
    print("Título: ")
    try:
        title = soup.find("h1",
                         {"id": "cuDetalle_cuTitular_tituloNoticia"})
        title_text = title.getText()
        print(title_text)
    except:
        print("Error al intentar recuperar título de noticia.")
        title_text = ""
    print("Contenido: ")
    try:
        content = soup.find("div",
                         {"id": "cuDetalle_cuTexto_textoNoticia"})
        content_text = content.getText()
        print(content_text)
    except:
        print("Error al intentar recuperar contenido de noticia.")
        content_text = ""
    print("")

    dict_new = {
        'id': count,
        'title': title_text,
        'content': content_text
    }
    list_news.append(dict_new)

    count += 1

with open("emol_data.json", 'w', encoding='utf-8') as outfile:
    json.dump(list_news, outfile, ensure_ascii=False)