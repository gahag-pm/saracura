src-dir = src
bin-dir = bin
lib-dir = lib

libs = ${lib-dir}/LGoodDatePicker-10.4.1.jar

units = $(shell find $(src-dir) -name '*.java')

main-class = br.ufmg.dcc.pm.saracura.Main


directories:
	@mkdir -p ${bin-dir}

build: directories
	@javac -cp ${libs} -d ${bin-dir} ${units}

run:
	@java -cp ${libs}:${bin-dir} ${main-class}


clean:
	@rm -rf ${bin-dir}
