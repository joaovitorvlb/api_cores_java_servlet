# Compilação manual do servlet, sem IDE.
#   make sintaxe   verifica a compilação e não gera o WAR
#   make teste     compila e roda os testes
#   make deploy    gera myservlet.war e publica em /opt/tomcat/webapps

CATALINA_HOME ?= /opt/tomcat
APP           := myservlet
MYSQL_JAR     := lib/mysql-connector-j-8.4.0.jar
CP            := lib/jakarta.servlet-api-6.1.0.jar:lib/json-20250107.jar:$(MYSQL_JAR)
CLASSES       := build/WEB-INF/classes
SRC           := $(shell find src -name '*.java')
TEST_SRC      := $(shell find teste -name '*.java')
TEST_CLASSES  := build/teste

.DEFAULT_GOAL := sintaxe
.PHONY: sintaxe teste war deploy clean

sintaxe: $(CLASSES)

$(CLASSES): $(SRC)
	mkdir -p $(CLASSES)
	javac --release 17 -encoding UTF-8 -cp "$(CP)" -d $(CLASSES) $(SRC)

teste: $(CLASSES)
	mkdir -p $(TEST_CLASSES)
	javac --release 17 -encoding UTF-8 -cp "$(CP):$(CLASSES)" -d $(TEST_CLASSES) $(TEST_SRC)
	java -cp "$(CP):$(CLASSES):$(TEST_CLASSES)" com.example.util.TesteRespostaErro

war: $(APP).war

$(APP).war: $(CLASSES) web/WEB-INF/web.xml lib/json-20250107.jar $(MYSQL_JAR)
	mkdir -p build/WEB-INF/lib
	cp web/WEB-INF/web.xml build/WEB-INF/web.xml
	cp lib/json-20250107.jar $(MYSQL_JAR) build/WEB-INF/lib/
	jar --create --file $(APP).war -C build .
	@echo "Gerado: $(APP).war"

deploy: $(APP).war
	sudo rm -rf $(CATALINA_HOME)/webapps/$(APP) $(CATALINA_HOME)/webapps/$(APP).war
	sudo install -o tomcat -g tomcat -m 640 $(APP).war $(CATALINA_HOME)/webapps/$(APP).war
	@echo "Publicado em $(CATALINA_HOME)/webapps/$(APP).war"

clean:
	rm -rf build $(APP).war
