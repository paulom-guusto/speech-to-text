package com.guusto.demo;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import com.google.cloud.speech.v1.RecognizeResponse;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;

@Service
public class SpeechRecognitionService {

	public String transcript(byte[] bytes) throws IOException {


		// Instantiates a client
		try (SpeechClient speechClient = SpeechClient.create()) {

			// The path to the audio file to transcribe
//			String gcsUri = "gs://cloud-samples-data/speech/brooklyn_bridge.raw";

			// Builds the sync recognize request
			RecognitionConfig config = RecognitionConfig.newBuilder().setEncoding(AudioEncoding.LINEAR16)
					.setSampleRateHertz(44100)
					.setAudioChannelCount(2)
					.setEnableAutomaticPunctuation(Boolean.TRUE)
					.setLanguageCode("en-US")
					.build();
			RecognitionAudio audio = RecognitionAudio.newBuilder().setUri("gs://guusto-poc-bucket/embedadapt_1sample.wav").build();

			// Performs speech recognition on the audio file
			RecognizeResponse response = speechClient.recognize(config, audio);
			List<SpeechRecognitionResult> results = response.getResultsList();

			for (SpeechRecognitionResult result : results) {
				// There can be several alternative transcripts for a given chunk of speech.
				// Just use the
				// first (most likely) one here.
				SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);

				return alternative.getTranscript();
			}

		}

		return null;

	}

}