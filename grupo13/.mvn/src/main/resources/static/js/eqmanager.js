//load weapon list
  fetch("http://localhost:8080/api/weapons")
    .then(response => response.json())
    .then(weapons => {
      const list = document.getElementById("weapon-list");
      weapons.forEach(weapon => {
        const item = document.createElement("li");
        item.className = "list-group-item";
        item.innerHTML = `<a href="weapon/${weapon.id}">${weapon.name}</a>`; //link to edit weapon
        list.appendChild(item);
      });
    })
    .catch(error => console.error("error", error));

//load armor list
  fetch("http://localhost:8080/api/armors")
    .then(response => response.json())
    .then(armors => {
      const list = document.getElementById("armor-list");
      armors.forEach(armor => {
        const item = document.createElement("li");
        item.className = "list-group-item";
        item.innerHTML = `<a href="armor/${armor.id}">${armor.name}</a>`; //link to edit armor
        list.appendChild(item);
      });
    })
    .catch(error => console.error("error", error));
