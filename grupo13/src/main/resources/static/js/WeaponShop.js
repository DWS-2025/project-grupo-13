let currentPage = 0
let totalPages = 0;
const itemsPerPage = 12; // Change here to change the numbers per page

document.addEventListener('DOMContentLoaded', function() {
    loadWeapons(currentPage);
});

function loadWeapons(page) {
    fetch(`/api/weapons?page=${page}&size=${itemsPerPage}`)
        .then(response => response.json())
        .then(data => {

        weapons = data.content;
        const weaponList = document.getElementById('weapon-list');
        weaponList.innerHTML = '';
                            
        if (weapons.length === 0) {
            weaponList.innerHTML = '<div class="col-12 text-center"><p>Nothing here. If you wanna spend more money, I acept bizum, paypal or revolut.</p></div>';
            return;
        }
                            
        weapons.forEach(weapon => {
            const weaponCard = document.createElement('div');
            weaponCard.className = 'col';
            weaponCard.innerHTML = `                        
                <div class="card" style="width:18rem;">
                    <a class="btn" href="/weapon/${weapon.id}">
                        <img src="api/weapon/${weapon.id}/image" class="card-img-top" alt="..." height="306px" width="1024px">
                        <div class="card-body">               
                            <h5 class="card-title">${weapon.name}</h5>
                        </div>
                    </a>
                </div>          
            `;                    
            weaponList.appendChild(weaponCard);
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
        loadWeapons(currentPage);
    }
});

document.getElementById('nextPage').addEventListener('click', () => {
    if (currentPage < totalPages - 1) {
        currentPage++;
        loadWeapons(currentPage);
    }
});

loadWeapons(currentPage);