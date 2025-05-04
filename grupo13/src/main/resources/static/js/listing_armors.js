let currentPage = 0;
let pageSize = 2;

function loadPage(page){
    fetch(`/api/armors?page=${page}&${pageSize}`)
        .then(response => response.json())
        .then(data => {
            const container = document.getElementById("armor-container");
            container.innerHTML = "";

            data.content.forEach(armor => {
                container.innerHTML +=`
                <div class="col">
                    <form action="purchaseArmor" method="post" enctype="multipart/form-data"
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
                        </div>
                    </form>
                </div>`;
            });
            currentPage = page;
            document.getElementById("pageNum").textContent = currentPage + 1;

            document.getElementById("prevBtn").disabled = !data.hasPrevious;
            document.getElementById("nextBtn").disabled = (page + 1) * pageSize >= data.totalElements;
        
        });
}
document.addEventListener("DOMContentLoaded", () => {
    loadPage(0);
});