class CreateDocuments < ActiveRecord::Migration[7.0]
  def change
    create_table :documents do |t|
      t.string :name
      t.string :description
      t.integer :document_type
      t.datetime :expiration_date
      t.datetime :emision_date
      t.references :user, null: false, foreign_key: true

      t.timestamps
    end
  end
end
