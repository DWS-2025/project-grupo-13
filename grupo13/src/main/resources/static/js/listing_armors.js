let page = 0;
let size = 3;
 
async function loadArmors() {
    const response = await fetch(`/api/armors?page=${page}&${pageSize}`);
    const data = await response.json();
    const container = document.getElementById("armor-container");
    container.innerHTML = "";
    
    data.armor.forEach(armor => {
        const div = document.createElement("div");
        div.className = "Armor";
        div.innerHTML = `
            <div class="card" style="width:18rem;">
                <img src="${armor.imageUrl}" class="card-img-top" alt="..." height="306px" width="1024px">
                <div class="card-body">
                    <h5 class="card-title">${armor.name}</h5>
                    <p class="card-text">${armor.description}</p>
                    <h6 class="card-price">Price: ${armor.price}</h6>
                    <h6 class="card-defense">Defense: ${armor.defense}</h6>
                    <h6 class="card-style">Style: ${armor.style}</h6>
                    <input type="hidden" name="id" value="${armor.id}">
                    <input type='submit' value='Add'>
                </div>
            </div>`;
            container.appendChild(div);
        });

        document.getElementById(currentPage).textContent = page + 1;
}

function loadPrevPage(){
    pagina--;
    loadArmors();
}
function loadNextPage(){
    pagina++;
    loadArmors();
}

window.onload = loadArmors;