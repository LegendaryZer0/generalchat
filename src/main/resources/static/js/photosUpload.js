$(document).ready(function () {
    let inpFile = document.getElementById("upload-photo-input");
    let previewContainer = document.getElementById('image-preview');
    let previewImage =document.getElementById('image-preview__image');

    inpFile.addEventListener("change",function (){
        console.log("state was changed?")
        const file = this.files[0];
        console.log(file)
        if(file){
            let reader = new FileReader();
            previewContainer.style.display="block";
            previewImage.style.display="block";

            reader.addEventListener("load",function () {
                console.log(this);
                previewImage.setAttribute("src",this.result);
            })
            reader.readAsDataURL(file);
        }else {
            previewContainer.style.display=null;
            previewImage.style.display=null;
        }
    });
});
