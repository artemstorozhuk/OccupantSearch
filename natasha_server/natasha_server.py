import json
from http.server import HTTPServer, BaseHTTPRequestHandler
from natasha import (
    Segmenter,
    MorphVocab,
    NewsEmbedding,
    NewsMorphTagger,
    NewsSyntaxParser,
    NewsNERTagger,
    Doc
)
from socketserver import ThreadingMixIn

segmenter = Segmenter()
morph_vocab = MorphVocab()
emb = NewsEmbedding()
morph_tagger = NewsMorphTagger(emb)
syntax_parser = NewsSyntaxParser(emb)
ner_tagger = NewsNERTagger(emb)

hostName = "localhost"
serverPort = 8081


class NatashaHandler(BaseHTTPRequestHandler):
    def do_POST(self):
        self.send_response(200)
        self.send_header("Content-type", "text/html")
        self.end_headers()

        content_len = int(self.headers.get("Content-Length", 0))
        post_body = self.rfile.read(content_len).decode("utf-8")

        doc = Doc(post_body)
        doc.segment(segmenter)
        doc.tag_morph(morph_tagger)
        doc.tag_ner(ner_tagger)
        for span in doc.spans:
            span.normalize(morph_vocab)

        locations = []
        for span in doc.spans:
            if span.type == 'LOC':
                locations.append(span.normal)

        result = {'locations': locations}
        self.wfile.write(json.dumps(result).encode("utf-8"))

    def log_message(self, format, *args):
        pass


class ThreadingSimpleServer(ThreadingMixIn, HTTPServer):
    pass


if __name__ == "__main__":
    webServer = ThreadingSimpleServer((hostName, serverPort), NatashaHandler)

    try:
        webServer.serve_forever()
    except KeyboardInterrupt:
        pass

    webServer.server_close()
