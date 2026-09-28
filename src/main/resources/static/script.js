const API_BASE = '/api';

// --- Delete Functions (Must be in global scope) ---

window.deleteFamily = async function(id) {
    if (!confirm('Are you sure you want to remove this family?')) return;
    try {
        const response = await fetch(`${API_BASE}/families/${id}`, { method: 'DELETE' });
        if (response.ok) {
            loadDashboard(); // Refresh all tables instantly
        } else {
            const errorText = await response.text();
            alert(`Error deleting family: ${errorText}`);
        }
    } catch (error) {
        console.error(error);
    }
};

window.deleteSupply = async function(id) {
    if (!confirm('Are you sure you want to delete this log?')) return;
    try {
        const response = await fetch(`${API_BASE}/supplies/${id}`, { method: 'DELETE' });
        if (response.ok) {
            loadDashboard(); // Refresh all tables instantly
        } else {
            const errorText = await response.text();
            alert(`Error deleting supply: ${errorText}`);
        }
    } catch (error) {
        console.error(error);
    }
};
document.addEventListener('DOMContentLoaded', () => {
    loadDashboard();
    setupEventListeners();
});

function setupEventListeners() {
    document.getElementById('campForm').addEventListener('submit', handleCampSubmit);
    document.getElementById('supplyForm').addEventListener('submit', handleSupplySubmit);
    document.getElementById('familyForm').addEventListener('submit', handleFamilySubmit);
    document.getElementById('distributionForm').addEventListener('submit', handleDistributionSubmit);

    document.getElementById('distCampId').addEventListener('change', populateDistributionDropdowns);
}

async function loadDashboard() {
    await fetchCamps();
    await fetchSupplies();
    await fetchFamilies();
}

// --- Fetch & Display Logic ---

async function fetchCamps() {
    try {
        const response = await fetch(`${API_BASE}/camps`);
        const camps = await response.json();

        const tbody = document.querySelector('#campsTable tbody');
        if (!tbody) return;
        tbody.innerHTML = '';

        const campSelects = [
            document.getElementById('supplyCampId'),
            document.getElementById('familyCampId'),
            document.getElementById('distCampId')
        ];

        campSelects.forEach(select => {
            if(select) select.innerHTML = '<option value="">Select Camp</option>';
        });

        camps.forEach(camp => {
            tbody.innerHTML += `
                <tr>
                    <td>${camp.id}</td>
                    <td>${camp.name}</td>
                    <td>${camp.location}</td>
                    <td>${camp.capacity}</td>
                    <td>${camp.currentOccupancy || 0}</td>
                </tr>
            `;

            campSelects.forEach(select => {
                if(select) select.innerHTML += `<option value="${camp.id}">${camp.name}</option>`;
            });
        });
    } catch (error) {
        console.error('Error fetching camps:', error);
    }
}

async function fetchSupplies() {
    try {
        const response = await fetch(`${API_BASE}/supplies`);
        const supplies = await response.json();

        const tbody = document.querySelector('#suppliesTable tbody');
        if (!tbody) return;
        tbody.innerHTML = '';

        supplies.forEach(supply => {
            const campId = supply.camp ? supply.camp.id : 'N/A';
            tbody.innerHTML += `
                <tr>
                    <td>${campId}</td>
                    <td>${supply.name}</td>
                    <td>${supply.type}</td>
                    <td>${supply.quantity}</td>
                    <td>
                        <button onclick="deleteSupply(${supply.id})" style="background-color: #dc2626; color: white; padding: 0.3rem 0.6rem; border: none; border-radius: 4px; cursor: pointer;">Delete</button>
                    </td>
                </tr>
            `;
        });

        window.currentSupplies = supplies;
    } catch (error) {
        console.error('Error fetching supplies:', error);
    }
}

async function fetchFamilies() {
    try {
        const response = await fetch(`${API_BASE}/families`);
        const families = await response.json();
        window.currentFamilies = families;

        const tbody = document.querySelector('#familiesTable tbody');
        if (!tbody) return; // Skips if you haven't added the familiesTable to HTML yet

        tbody.innerHTML = '';
        families.forEach(family => {
            const campId = family.camp ? family.camp.id : 'N/A';
            tbody.innerHTML += `
                <tr>
                    <td>${campId}</td>
                    <td>${family.headName}</td>
                    <td>${family.contactNumber || 'N/A'}</td>
                    <td>${family.headcount}</td>
                    <td>
                        <button onclick="deleteFamily(${family.id})" style="background-color: #dc2626; color: white; padding: 0.3rem 0.6rem; border: none; border-radius: 4px; cursor: pointer;">Delete</button>
                    </td>
                </tr>
            `;
        });
    } catch (error) {
        console.error('Error fetching families:', error);
    }
}

function populateDistributionDropdowns(e) {
    const campId = e.target.value;
    const familySelect = document.getElementById('distFamilyId');
    const supplySelect = document.getElementById('distSupplyId');

    if(familySelect) familySelect.innerHTML = '<option value="">Select Family</option>';
    if(supplySelect) supplySelect.innerHTML = '<option value="">Select Supply</option>';

    if (!campId) return;

    window.currentFamilies
        .filter(f => (f.camp && f.camp.id == campId))
        .forEach(family => {
            familySelect.innerHTML += `<option value="${family.id}">${family.headName} (Size: ${family.headcount})</option>`;
        });

    window.currentSupplies
        .filter(s => (s.camp && s.camp.id == campId) && s.quantity > 0)
        .forEach(supply => {
            supplySelect.innerHTML += `<option value="${supply.id}">${supply.name} (Available: ${supply.quantity})</option>`;
        });
}

// --- Form Submit Handlers ---

async function handleCampSubmit(e) {
    e.preventDefault();
    const payload = {
        name: document.getElementById('campName').value,
        location: document.getElementById('campLocation').value,
        capacity: parseInt(document.getElementById('campCapacity').value),
        currentOccupancy: 0
    };
    await postData(`${API_BASE}/camps`, payload);
    e.target.reset();
    loadDashboard();
}

async function handleSupplySubmit(e) {
    e.preventDefault();
    const payload = {
        camp: { id: parseInt(document.getElementById('supplyCampId').value) },
        name: document.getElementById('supplyName').value,
        type: document.getElementById('supplyType').value,
        quantity: parseInt(document.getElementById('supplyQuantity').value)
    };
    await postData(`${API_BASE}/supplies`, payload);
    e.target.reset();
    loadDashboard();
}

async function handleFamilySubmit(e) {
    e.preventDefault();
    const payload = {
        camp: { id: parseInt(document.getElementById('familyCampId').value) },
        headName: document.getElementById('familyHead').value,
        contactNumber: document.getElementById('familyContact').value,
        headcount: parseInt(document.getElementById('familyHeadcount').value)
    };
    await postData(`${API_BASE}/families`, payload);
    e.target.reset();
    loadDashboard();
}

async function handleDistributionSubmit(e) {
    e.preventDefault();
    const payload = {
        camp: { id: parseInt(document.getElementById('distCampId').value) },
        family: { id: parseInt(document.getElementById('distFamilyId').value) },
        supply: { id: parseInt(document.getElementById('distSupplyId').value) },
        quantity: parseInt(document.getElementById('distQuantity').value)
    };
    await postData(`${API_BASE}/distributions`, payload);
    e.target.reset();
    loadDashboard();
}

async function postData(url, data) {
    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });

        if (!response.ok) {
            const errorText = await response.text();
            alert(`Error: ${errorText}`);
        } else {
            alert('Success!');
        }
    } catch (error) {
        alert('Network error. Make sure your Spring Boot backend is running.');
        console.error(error);
    }
}

// --- Delete Functions (Must be in global scope) ---

window.deleteFamily = async function(id) {
    if (!confirm('Are you sure you want to remove this family?')) return;
    try {
        const response = await fetch(`${API_BASE}/families/${id}`, { method: 'DELETE' });
        if (response.ok) {
            loadDashboard(); // Refresh all tables instantly
        } else {
            const errorText = await response.text();
            alert(`Error deleting family: ${errorText}`);
        }
    } catch (error) {
        console.error(error);
    }
};

window.deleteSupply = async function(id) {
    if (!confirm('Are you sure you want to delete this log?')) return;
    try {
        const response = await fetch(`${API_BASE}/supplies/${id}`, { method: 'DELETE' });
        if (response.ok) {
            loadDashboard(); // Refresh all tables instantly
        } else {
            const errorText = await response.text();
            alert(`Error deleting supply: ${errorText}`);
        }
    } catch (error) {
        console.error(error);
    }
};