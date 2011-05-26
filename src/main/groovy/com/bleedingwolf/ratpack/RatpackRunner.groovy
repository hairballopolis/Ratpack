package com.bleedingwolf.ratpack;

import groovy.lang.Binding
import groovy.util.GroovyScriptEngine
import com.bleedingwolf.ratpack.RatpackServlet
import com.bleedingwolf.ratpack.RatpackApp

class RatpackRunner {
	RatpackApp app = new RatpackApp()
	
	void run(File scriptFile) {
		def root = parentDirectoryName(scriptFile)
		GroovyScriptEngine gse = new GroovyScriptEngine(root)

		Binding binding = new Binding()
		binding.setVariable('get', app.get)
		binding.setVariable('post', app.post)
		binding.setVariable('put', app.put)
		binding.setVariable('delete', app.delete)
		binding.setVariable('set', app.set)

		gse.run scriptFile.name, binding

		app.set 'root', root
		RatpackServlet.serve(app)
	}
	
	private String parentDirectoryName(File scriptFile) {
		scriptFile.canonicalFile.parent
	}
}
