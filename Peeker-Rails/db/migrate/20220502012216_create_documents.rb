# frozen_string_literal: true

class CreateDocuments < ActiveRecord::Migration[7.0]
  def change
    create_table :documents, id: :uuid do |t|
      t.string :name
      t.string :description
      t.integer :document_type
      t.boolean :favorite, default: false
      t.datetime :expiration_date
      t.datetime :emission_date
      t.references :user, null: false, foreign_key: true, type: :uuid

      t.timestamps
    end
  end
end
