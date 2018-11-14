src-dir = src
bin-dir = bin

units  = $(shell find $(src-dir) -name '*.java')

main-class = br.ufmg.dcc.pm.saracura.Main


directories:
	@mkdir -p ${bin-dir}

build: directories
	@javac -d ${bin-dir} ${units}

run:
	@java -classpath ${bin-dir} ${main-class}


clean:
	@rm -rf ${bin-dir}
