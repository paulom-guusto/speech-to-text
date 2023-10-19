package com.guusto.demo;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class SpeechRecognitionEndpoint {
	
	public class SpeechRecognitionResponse {
		
		private String message;
		
		public void setMessage(String message) {
			this.message = message;
		}
		
		public String getMessage() {
			return message;
		}
		
	}

	@Autowired
	private SpeechRecognitionService speechRecognitionService;

	@PostMapping("/")
	@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "Requestor-Type", exposedHeaders = "X-Get-Header")
	public SpeechRecognitionResponse handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
		SpeechRecognitionResponse response= new SpeechRecognitionResponse();
		response.setMessage(speechRecognitionService.transcript(file.getBytes()));
		return response;
	}

}
