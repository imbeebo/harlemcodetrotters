
function handleDrag(event) {
	event.preventDefault();
}

var uploadId = 0;

function uploadFiles(event) {
	event.preventDefault();

	var files = event.dataTransfer.files;
	var fileQueue = [];
	var fragment = document.createDocumentFragment();
	for(var i = 0; i < files.length; i++) {
		var id = uploadId++;
		var file = files.item(i);

		var nameSpan = document.createElement('span');
		nameSpan.className = 'upload-filename';
		nameSpan.textContent  = file.name;

		var uploadProgress = document.createElement('progress');
		uploadProgress.className = 'upload-progress-bar';
		uploadProgress.value = 0;
		uploadProgress.max = 100;

		var div = document.createElement('div');
		div.className = 'upload-progress';
		div.appendChild(nameSpan);
		div.appendChild(uploadProgress);
		fragment.appendChild(div);

		fileQueue.push({id: id, file:file, bar: uploadProgress});
	}
	document.getElementById('uploadList').appendChild(fragment);

	sendFiles(fileQueue);
}

function sendFiles(queue) {
	if(queue.length < 1)
		return;

	var file = queue.shift();
	var req = new XMLHttpRequest();

	req.onreadystatechange = function() {
		if(req.readyState === XMLHttpRequest.DONE) {
			file.bar.value = 100;
			sendFiles(queue);
		}
	};

	req.upload.onprogress = function(event) {
		file.bar.value = event.loaded * 100 / event.total;
	};

	req.open('POST', '/GonqBox/upload');

	var form = new FormData();
	form.append('file', file.file);
	req.send(form);
}