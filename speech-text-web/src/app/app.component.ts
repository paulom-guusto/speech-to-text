import { Component } from '@angular/core'
import * as RecordRTC from 'recordrtc'
import { DomSanitizer } from '@angular/platform-browser'
import { faMicrophoneAlt, faTrashAlt, faGift } from '@fortawesome/free-solid-svg-icons'
import { faCommentAlt } from '@fortawesome/free-regular-svg-icons'
import { AppService } from './app.service'

declare var $: any;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
	faMicrophone = faMicrophoneAlt;
	faTrash = faTrashAlt
	faGift = faGift
	faComment = faCommentAlt
	message = ''
	record;
	recording = false;
	url;
	audioFile;
	error;
	
	constructor(private domSanitizer: DomSanitizer, 
	            private appService: AppService) {}
	
	sanitize(url: string) {
		return this.domSanitizer.bypassSecurityTrustUrl(url);
	}

	startRecording() {
		this.audioFile = null;
		this.url = null;
		this.message = null;
		this.recording = true;
		let mediaConstraints = {
			video: false,
			audio: true
		};
		navigator.mediaDevices
			.getUserMedia(mediaConstraints)
			.then(this.successCallback.bind(this), 
				this.errorCallback.bind(this));
	}

	successCallback(stream) {
		var options = {
			mimeType: "audio/wav",
			numberOfAudioChannels: 1,
		};

		var StereoAudioRecorder = RecordRTC.StereoAudioRecorder;
		this.record = new StereoAudioRecorder(stream, options);
		this.record.record();
	}

	stopRecording() {
		this.recording = false;
		this.record.stop(this.processRecording.bind(this));
	}

	processRecording(blob) {
		this.audioFile = blob;
		this.url = URL.createObjectURL(blob);
		console.log("blob", blob);
		console.log("url", this.url);
	}

	errorCallback(error) {
		this.error = 'Can not play audio in your browser';
	}

	convertToText() {
		this.appService.convertMessage(this.audioFile)
			.subscribe(data => {
				this.message = data.message
			})
	}
	
	clearMessage() {
		this.message = ''
	}
}
