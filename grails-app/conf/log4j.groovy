'log4j:configuration'("xmlns:log4j": "http://jakarta.apache.org/log4j/", debug: "false") {
	
	  /**
	   * Appender: single entry - file
	   */
	
	  appender(name: "EMAIL_LOG", 'class': "org.apache.log4j.net.SMTPAppender") {
		errorHandler 'class': "org.apache.log4j.helpers.OnlyOnceErrorHandler"
		param name: "threshold", value: "TRACE"
		param name: "to", value: "dom061077@gmail.com"
		param name: "from", value:"from@pezon.com"
		param name: "subject", value:"log4j"
		param name: "SMTPHost", value:"smtp.gmail.com"
		param name: "SMTPUsername", value:"xxx@gmail.com"
		param name: "SMTPDebug",value:"true"
		param name: "SMTPPassword", value:"xxx"
		
		layout('class': "org.apache.log4j.PatternLayout") {
		  param name: "ConversionPattern", value: "%d %-5p [%-30.40c{1}] %2X{tid} %X{sid} %X{uid} %m%n"
		}
	  }
	
	
	
	  appender(name: "APP_LOG", 'class': "org.apache.log4j.DailyRollingFileAppender") {
		errorHandler 'class': "org.apache.log4j.helpers.OnlyOnceErrorHandler"
		param name: "File", value: "logs/appcima.log"
		param name: "Append", value: "false"
		layout('class': "org.apache.log4j.PatternLayout") {
		  param name: "ConversionPattern", value: "%d %-5p [%-30.40c{1}] %2X{tid} %X{sid} %X{uid} %m%n"
		}
	  }
	
	  /**
	   * Appender: shared definition
	   */
	  ['ORM_LOG', 'ORM_SQL_LOG', 'CACHE_LOG'].each {loggerName ->
		appender(name: loggerName, 'class': "org.apache.log4j.DailyRollingFileAppender") {
		  errorHandler 'class': "org.apache.log4j.helpers.OnlyOnceErrorHandler"
		  param name: "File", value: "logs/${loggerName.toLowerCase()}cima.log"
		  param name: "Append", value: "false"
		  param name: "DatePattern", value: "'.'yyyy-MM-dd"
		  layout('class': "org.apache.log4j.PatternLayout") {
			param name: "ConversionPattern", value: "%d %-5p [%-30.40c{1}] %2X{tid} %X{sid} %X{uid} %m%n"
		  }
		}
	  }
	
	  /**
	   * Appender: single entry - console
	   */
	  appender(name: "CONSOLE", 'class': "org.apache.log4j.ConsoleAppender") {
		errorHandler 'class': "org.apache.log4j.helpers.OnlyOnceErrorHandler"
		param name: "Target", value: "System.out"
		param name: "Threshold", value: "TRACE"
		layout('class': "org.apache.log4j.PatternLayout") {
		  param name: "ConversionPattern", value: "%d{ABSOLUTE} %-5p [%c{1}][%X{tid}] %m%n"
		}
	  }
	
	  /**
	   * Category - single entry
	   */
	  'category'(name: "org.grails.plugins.log4jxml", additivity: "false") {
		'priority' value: "TRACE"
		'appender-ref' ref: "CONSOLE"
	  }
	
	  /**
	   * Category - shared definition by log level
	   */
	  def levels = [:]
	  levels.'warn' = ['org.jgroups']
	  levels.'info' = ['org.apache', 'org.quartz', 'org.jboss', 'org.springframework', 'org.codehaus', 'org.mortbay'] +
			  ['groovy', 'grails']
	  levels.'debug' = []
	
	  levels.each {level, packages ->
		packages.each {
		  'category'(name: it, additivity: 'false') {
			'priority'(value: level.toUpperCase())
		  }
		}
	  }
	
	  /**
	   * Category - shared definition by custom groupping
	   */
	  def appemail = ['grails.app']
	  appemail.each {
		'category'(name: it, additivity: 'true') {
		  'priority'(value: 'TRACE'); 'appender-ref'('ref': "EMAIL_LOG");
		}
	  }

	  
	  def app = ['grails.app']
	  app.each {
		'category'(name: it, additivity: 'true') {
		  'priority'(value: 'DEBUG'); 'appender-ref'('ref': "APP_LOG"); 'appender-ref'('ref': "CONSOLE")}
	  }
	
	  def orm = ['org.springframework.transaction', 'org.springframework.orm', 'org.apache.openjpa', 'openjpa', 'org.h2', 'h2database','org.hibernate','org.codehaus.groovy.grails.orm.hibernate']
	  orm.each {
		'category'(name: it, additivity: 'false') { 'priority'(value: 'INFO'); 'appender-ref'('ref': "ORM_LOG")}
	  }
	
	  def sql = ['org.hibernate.SQL', 'openjpa.jdbc.SQL']
	  sql.each {
		'category'(name: it, additivity: 'false') { 'priority'(value: 'DEBUG'); 'appender-ref'('ref': "ORM_SQL_LOG")}
	  }
	
	  def cache = ['org.jboss.cache']
	  cache.each {
		'category'(name: it, additivity: 'false') { 'priority'(value: 'DEBUG'); 'appender-ref'('ref': "CACHE_LOG")}
	  }
	
	  /**
	   * Category - root definition
	   */
	  'root' {
		'priority' value: "INFO"
		'appender-ref' ref: "CONSOLE"
	  }
	}
		  