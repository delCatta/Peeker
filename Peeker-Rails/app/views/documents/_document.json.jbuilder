# frozen_string_literal: true

json.extract! document, :id, :name, :description, :document_type, :favorite, :expiration_date, :emission_date, :user_id,
              :created_at, :updated_at
json.file_url document.file.attached? ? url_for(document.file, only_path: false) : ''
json.tags document.tags, :id, :name, :user_id, :updated_at, :created_at
json.url document_url(document, format: :json)
