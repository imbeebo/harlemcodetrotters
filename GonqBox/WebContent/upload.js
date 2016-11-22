
function handleDrag(event) {
	event.preventDefault();
}

var uploadQueue = [];
var uploadWorkers = 0;
var maxSimultaneousUploads = 3;

function dropFiles(event) {
	event.preventDefault();
	uploadFiles(event.dataTransfer.files);
}

function submitFiles(event) {
	event.preventDefault();
	uploadFiles(document.getElementById('upload-files').files);
}

function uploadFiles(files) {
	var fragment = document.createDocumentFragment();
	for(var i = 0; i < files.length; i++) {
		var file = files.item(i);

		var nameSpan = document.createElement('span');
		nameSpan.className = 'upload-filename';
		nameSpan.textContent  = file.name;

		var uploadProgress = document.createElement('progress');
		uploadProgress.className = 'progress';
		uploadProgress.value = 0;
		uploadProgress.max = 100;

		var div = document.createElement('li');
		div.className = 'upload-progress';
		div.appendChild(nameSpan);
		div.appendChild(uploadProgress);
		fragment.appendChild(div);

		uploadQueue.push({file:file, bar: uploadProgress});
	}

	document.getElementById('uploadList').appendChild(fragment);

	while(uploadWorkers < maxSimultaneousUploads && uploadQueue.length) {
		uploadWorkers++;
		sendFiles();
	}

	$('#upload-list-dropdown').dropdown('toggle');
}

function sendFiles() {
	if(uploadQueue.length < 1) {
		uploadWorkers--;
		return;
	}

	var file = uploadQueue.shift();
	var req = new XMLHttpRequest();
	req.responseType = "text";

	req.onreadystatechange = function() {
		if(req.readyState === XMLHttpRequest.DONE) {
			file.bar.value = 100;

			if(req.status === 200) {
				file.bar.className = 'progress progress-success';
			} else {
				file.bar.className = 'progress progress-danger';
				if(req.status === 500) {
					var errorList = document.createElement('ul');
					errorList.innerHTML = req.response;
					file.bar.parentElement.appendChild(errorList);
				}
			}
			sendFiles();
		}
	};

	req.upload.onprogress = function(event) {
		if(event.lengthComputable)
			file.bar.value = event.loaded * 100 / event.total;
		else
			; // TODO: bar class for "unknown time remaining"
	};

	req.open('POST', '/GonqBox/upload?ajax=1');

	var form = new FormData();
	form.append('upload-files', file.file);
	req.send(form);
	file.bar.className = 'progress progress-striped progress-animated'
}
