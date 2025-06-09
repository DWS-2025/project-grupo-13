let currentPage = 0
let totalPages = 0;
const itemsPerPage = 6; // Change here to change the numbers per page

document.addEventListener('DOMContentLoaded', function() {
    loadArmors(currentPage);
});

function loadArmors(page) {
    fetch(`/api/armors?page=${page}&size=${itemsPerPage}`)
        .then(response => response.json())
        .then(data => {

        armors = data.content;
        const armorList = document.getElementById('armor-list');
        armorList.innerHTML = '';
                    
        if (armors.length === 0) {
            armorList.innerHTML = '<div class="col-12 text-center"><p>Nothing here. If you wanna spend more money, I acept bizum, paypal or revolut.</p></div>';
            return;
        }
                    
        armors.forEach(armor => {
            const armorCard = document.createElement('div');
            armorCard.className = 'col';
            armorCard.innerHTML = `                        
                <form action="/purchaseArmor" method="post" enctype="multipart/form-data">
                <div class="card" style="width:18rem;">
                    <img src="api/armor/${armor.id}/image" class="card-img-top" alt="..." height="306px" width="1024px">
                    <div class="card-body">
                        <h5 class="card-title">${armor.name}</h5>
                        <p class="card-text">${armor.description}</p>
                        <h6 class="card-price">Price: ${armor.price}</h6>
                        <h6 class="card-defense">Defense: ${armor.defense}</h6>
                        <h6 class="card-style">Style: ${armor.style}</h6>
                                    
                        <input type='submit' value='Buy'>
                    </div>
                </div>         
            `;                    
            armorList.appendChild(armorCard);
        });
        totalPages=data.page.totalPages;
        currentPage=page;

        document.getElementById('prevPage').disabled = data.first;
        document.getElementById('nextPage').disabled = data.last;
    });
}

document.getElementById('prevPage').addEventListener('click', () => {
    if (currentPage > 0) {
        currentPage--;
        loadArmors(currentPage);
    }
});

document.getElementById('nextPage').addEventListener('click', () => {
    if (currentPage < totalPages - 1) {
        currentPage++;
        loadArmors(currentPage);
    }
});

loadArmors(currentPage);